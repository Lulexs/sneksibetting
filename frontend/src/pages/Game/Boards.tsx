import { Divider, Flex, Stack, Text } from "@mantine/core";

export interface BoardsProps {
  my_board: number[][];
  opp_board: number[][];
  my_username: string;
  opp_username: string;
  my_elo: number;
  opp_elo: number;
  elo_gain_if_win: number;
  elo_gain_if_lose: number;
  elo_gain_if_draw: number;
  num_watching: number;
}

export interface BoardProps {
  board: number[][];
}

function OneBoard({ board }: BoardProps) {
  return (
    <Flex direction="column" m="xl" style={{ width: "max-content" }}>
      {board.map((row, rowIndex) => (
        <Flex key={rowIndex}>
          {row.map((cell, colIndex) => (
            <Flex
              key={colIndex}
              justify="center"
              align="center"
              style={{
                width: "50px",
                height: "50px",
                backgroundColor: "white",
                border: "1px solid black",
              }}
            >
              {cell !== 0 && (
                <div
                  style={{
                    width: "22px",
                    height: "22px",
                    backgroundColor: cell === 1 ? "black" : "green",
                  }}
                />
              )}
            </Flex>
          ))}
        </Flex>
      ))}
    </Flex>
  );
}

export default function Boards({
  my_board,
  opp_board,
  my_username,
  opp_username,
  my_elo,
  opp_elo,
  elo_gain_if_draw,
  elo_gain_if_lose,
  elo_gain_if_win,
  num_watching,
}: BoardsProps) {
  return (
    <Flex direction="column" align="center" gap="lg" style={{ width: "100%" }}>
      <Text size="sm" c="dimmed">
        👁️ {num_watching} {num_watching === 1 ? "person" : "people"} watching
      </Text>

      <Flex justify="space-evenly" align="flex-start" style={{ width: "100%" }}>
        <Stack align="center" gap="xs">
          <Text fw={700}>{my_username}</Text>
          <Text size="sm" c="dimmed">
            ELO: {my_elo}
          </Text>
          <Text size="xs" c="gray">
            +{elo_gain_if_win} if win / {elo_gain_if_draw} if draw /{" "}
            {elo_gain_if_lose} if lose
          </Text>
          <OneBoard board={my_board} />
        </Stack>

        <Divider orientation="vertical" />

        <Stack align="center" gap="xs">
          <Text fw={700}>{opp_username}</Text>
          <Text size="sm" c="dimmed">
            ELO: {opp_elo}
          </Text>
          <Text size="xs" c="gray">
            -
          </Text>
          <OneBoard board={opp_board} />
        </Stack>
      </Flex>
    </Flex>
  );
}
