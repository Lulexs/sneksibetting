import { Button, Flex, Group } from "@mantine/core";
import { useNavigate } from "react-router";
import useWebSocket from "react-use-websocket";

function getBytes(type: number, message: any) {
  const json = JSON.stringify(message);
  const jsonBytes = new TextEncoder().encode(json);

  const buffer = new ArrayBuffer(4 + jsonBytes.length);
  const view = new DataView(buffer);
  view.setInt32(0, type);
  new Uint8Array(buffer, 4).set(jsonBytes);

  return buffer;
}

export default function HomePage() {
  const navigate = useNavigate();

  const socketUrl = "ws://localhost:8080/ws";

  const {sendMessage, lastMessage} = useWebSocket(socketUrl);


  return (
    <Flex direction="column" m="lg">
      {/* <Group>
        <Button onClick={() => navigate("/login")}>Login</Button>
        <Button onClick={() => navigate("/register")}>Register</Button>
      </Group>
      <h1>WELCOME TO SNEKSI BETTING</h1> */}
      <Button onClick={() => {sendMessage(getBytes(1, {userId: "5516e359-6c9c-4ebb-a409-52373d536d50"}))}}>Clickme</Button>
      <h5>{lastMessage?.data}</h5>
    </Flex>
  );
}
