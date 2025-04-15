import { useEffect, useState } from "react";
import { useStore } from "../../stores/store";
import { useNavigate } from "react-router";
import { Flex } from "@mantine/core";
import { BoardsProps } from "./Boards";
import { GameStartNoBets, TypedMessage, UpdateGameStateMessage } from "../../models/Message";
import useWebSocket from "react-use-websocket";
import Boards from "./Boards";

function getBytes(type: number, message: any) {
  const json = JSON.stringify(message);
  const jsonBytes = new TextEncoder().encode(json);

  const buffer = new ArrayBuffer(4 + jsonBytes.length);
  const view = new DataView(buffer);
  view.setInt32(0, type);
  new Uint8Array(buffer, 4).set(jsonBytes);

  return buffer;
}

async function getMessage(buf: Blob): Promise<TypedMessage> {
  if (buf == null || buf == undefined) {
    return { id: 0, message: { id: 0 } };
  }
  console.log(buf);
  const dv = new DataView(await buf.arrayBuffer());

  const type = dv.getInt32(0);

  switch (type) {
    case 2:
      return { id: type, message: {} };

    case 3: {
      const buffer = await buf.arrayBuffer();
      const jsonBytes = new Uint8Array(buffer, 4 + 2);
      const jsonString = new TextDecoder().decode(jsonBytes);
      try {
        const json = JSON.parse(jsonString);
        return { id: type, message: json };
      } catch (e) {
        console.error("Failed to parse JSON:", e);
        return { id: type, message: {} };
      }
    }

    case 4: {
      const buffer = await buf.arrayBuffer();
      const jsonBytes = new Uint8Array(buffer, 4 + 2);
      const jsonString = new TextDecoder().decode(jsonBytes);
      try {
        const json = JSON.parse(jsonString);
        return {id: type, message: json}
      } catch (e) {
        console.error("Failed to parse JSON", e);
        return {id: type, message: {}}
      }
    }


    default:
      return { id: 0, message: {} };
  }
}

export default function PlayGame() {
  const { userStore } = useStore();
  const navigate = useNavigate();

  const socketUrl = "ws://localhost:8080/ws";

  const { sendMessage, lastMessage } = useWebSocket(socketUrl);
  const [state, setState] = useState(0);

  const [gameState, setGameState] = useState<BoardsProps | null>(null);
  const [isPlayer1, setIsPlayer1] = useState<boolean>(false);

  useEffect(() => {
    if (userStore.user == null) {
      navigate("/");
    }
  }, [userStore.user]);

  useEffect(() => {
    if (state == 0) {
      sendMessage(getBytes(1, { userId: userStore.user?.uuid }));
    }
  }, []);

  useEffect(() => {
    const handleKeyDown = (event: any) => {
      let msg : ArrayBuffer | null = null;
      switch (event.key) {
        case 'ArrowUp':
          msg = getBytes(5, {gameId: gameState, isPlayer1: isPlayer1, direction: "u"})
          break;
        case 'ArrowDown':
          msg = getBytes(5, {gameId: gameState, isPlayer1: isPlayer1, direction: "d"})
          break;
        case 'ArrowLeft':
          msg = getBytes(5, {gameId: gameState, isPlayer1: isPlayer1, direction: "l"})
          break;
        case 'ArrowRight':
          msg = getBytes(5, {gameId: gameState, isPlayer1: isPlayer1, direction: "r"})
          break;
        default:
          break;
      }
      if (msg != null) {
        sendMessage(msg);
      }
    };

    window.addEventListener('keydown', handleKeyDown);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, []);

  useEffect(() => {
    const processMessage = async () => {
      if (lastMessage && lastMessage.data instanceof Blob) {
        const parsedMessage = await getMessage(lastMessage.data);
        console.log(parsedMessage.id);

        if (parsedMessage.id === 2) {
          setState(2);
        } else if (parsedMessage.id === 3) {
          const msg = parsedMessage.message as GameStartNoBets;
          const isPlayer1 = userStore.user?.username === msg.player1Username;
          setIsPlayer1(isPlayer1);

          const boardProps: BoardsProps = {
            gameId: msg.gameId,
            my_board: isPlayer1 ? msg.player1Board : msg.player2Board,
            opp_board: isPlayer1 ? msg.player2Board : msg.player1Board,
            my_username: isPlayer1 ? msg.player1Username : msg.player2Username,
            opp_username: isPlayer1 ? msg.player2Username : msg.player1Username,
            my_elo: isPlayer1 ? msg.player1Elo : msg.player2Elo,
            opp_elo: isPlayer1 ? msg.player2Elo : msg.player1Elo,
            elo_gain_if_win: msg.eloIfP1Wins,
            elo_gain_if_lose: -msg.eloIfP1Lose,
            elo_gain_if_draw: msg.eloIfDraw,
            num_watching: msg.numWatching,
            my_score: isPlayer1 ? msg.player1Score : msg.player2Score,
            opp_score: isPlayer1 ? msg.player2Score : msg.player1Score,
          };

          setGameState(boardProps);
          setState(3);
        } else if (parsedMessage.id === 4) {
          const msg = parsedMessage.message as UpdateGameStateMessage;
          const isPlayer1 = userStore.user?.username === msg.player1Username;
          setIsPlayer1(isPlayer1);

          const boardProps: BoardsProps = {
            gameId: gameState?.gameId!,
            my_board: isPlayer1 ? msg.player1Board : msg.player2Board,
            opp_board: isPlayer1 ? msg.player2Board : msg.player1Board,
            my_username: isPlayer1 ? msg.player1Username : msg.player2Username,
            opp_username: isPlayer1 ? msg.player2Username : msg.player1Username,
            my_elo: gameState?.my_elo!,
            opp_elo: gameState?.opp_elo!,
            elo_gain_if_win: gameState?.elo_gain_if_win!,
            elo_gain_if_lose: gameState?.elo_gain_if_lose!,
            elo_gain_if_draw: gameState?.elo_gain_if_draw!,
            num_watching: msg.numWatching,
            my_score: isPlayer1 ? msg.player1Score : msg.player2Score,
            opp_score: isPlayer1 ? msg.player2Score : msg.player1Score,
          };

          setGameState(boardProps);
        }
      }
    };

    processMessage();
  }, [lastMessage]);

  useEffect(() => {
    if (userStore.user == null) {
      navigate("/");
    }
  }, [userStore.user]);

  return (
    <Flex m="lg" h="85vh" align="center" justify="center">
      {state == 0 && <h1>Waiting for server</h1>}
      {state == 2 && <h1>Waiting for opponent</h1>}
      {state == 3 && <Boards {...gameState!} />}
    </Flex>
  );
}
