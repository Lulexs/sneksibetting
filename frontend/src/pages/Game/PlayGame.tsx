import { useEffect } from "react";
import { useStore } from "../../stores/store";
import { useNavigate } from "react-router";
import { Flex } from "@mantine/core";
import Board, { BoardsProps } from "./Boards";

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

export default function PlayGame() {
  const { userStore } = useStore();
  const navigate = useNavigate();

  useEffect(() => {
    if (userStore.user == null) {
      navigate("/");
    }
  }, [userStore.user]);

  return (
    <Flex m="lg" h="85vh" align="center" justify="center">
      <Board {...testObj} />
    </Flex>
  );
}
