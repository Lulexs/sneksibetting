import { Flex, Title } from "@mantine/core";
import { useStore } from "../../stores/store";
import { useEffect } from "react";
import { useNavigate } from "react-router";

export default function MainMenu() {
  const { userStore } = useStore();
  const navigate = useNavigate();

  useEffect(() => {
    if (userStore.user == null) {
      navigate("/");
    }
  }, [userStore.user]);

  return (
    <Flex h="85vh" direction="column" justify="center" align="center">
      <Title mb="xl">Welcome {userStore.user?.username}</Title>
      <Flex direction="row" gap="xl">
        <Flex
          w="200px"
          h="200px"
          justify="center"
          align="center"
          bg="rgb(190, 190, 190)"
          fz="h3"
          onClick={() => navigate("/playgame")}
        >
          Play game
        </Flex>
        <Flex
          w="200px"
          h="200px"
          justify="center"
          align="center"
          bg="rgb(190, 190, 190)"
          fz="h3"
        >
          Bet
        </Flex>
        <Flex
          w="200px"
          h="200px"
          justify="center"
          align="center"
          bg="rgb(190, 190, 190)"
          fz="h3"
        >
          Profile
        </Flex>
      </Flex>
    </Flex>
  );
}
