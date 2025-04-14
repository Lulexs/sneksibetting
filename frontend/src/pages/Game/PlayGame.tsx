import { useEffect, useState } from "react";
import { useStore } from "../../stores/store";
import { useNavigate } from "react-router";
import { Flex } from "@mantine/core";
import Board, { BoardsProps } from "./Boards";
import { TypedMessage } from "../../models/Message";
import useWebSocket from "react-use-websocket";

const booleanMatrix: number[][] = Array.from({ length: 10 }, () =>
  Array(10).fill(0)
);

booleanMatrix[0][0] = 1;
booleanMatrix[0][1] = 1;
booleanMatrix[0][2] = 1;

booleanMatrix[1][0] = 1;

booleanMatrix[5][5] = 2;

const testObj: BoardsProps = {
  my_board: booleanMatrix,
  opp_board: booleanMatrix,
  my_username: "Lule",
  opp_username: "TestUser",
  my_elo: 1000,
  opp_elo: 988,
  elo_gain_if_draw: -1,
  elo_gain_if_lose: -5,
  elo_gain_if_win: 3,
  num_watching: 4,
};

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
  console.log(typeof buf);
  console.log(buf);
  const dv = new DataView(await buf.arrayBuffer());

  const type = dv.getInt32(0);

  switch (type) {
    case 2:
      return { id: type, message: {} };

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
    const processMessage = async () => {
      if (lastMessage && lastMessage.data instanceof Blob) {
        const parsedMessage = await getMessage(lastMessage.data);
        if (parsedMessage.id == 2) {
          setState(2);
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
      {state == 4 && <Board {...testObj} />}
    </Flex>
  );
}
