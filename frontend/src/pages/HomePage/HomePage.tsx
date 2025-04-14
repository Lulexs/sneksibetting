import { Button, Flex, Group } from "@mantine/core";
import { useNavigate } from "react-router";



export default function HomePage() {
  const navigate = useNavigate();



  return (
    <Flex direction="column" m="lg">
      <Group>
        <Button onClick={() => navigate("/login")}>Login</Button>
        <Button onClick={() => navigate("/register")}>Register</Button>
      </Group>
      <h1>WELCOME TO SNEKSI BETTING</h1>
    </Flex>
  );
}
