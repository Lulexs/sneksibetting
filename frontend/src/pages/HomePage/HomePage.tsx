import { Button, Flex, Group } from "@mantine/core";
import { useEffect } from "react";
import { useNavigate } from "react-router";
import useWebSocket from "react-use-websocket";
import { QueuedMessage, TypedMessage } from "../../models/Message";

function getBytes(type: number, message: any) {
  const json = JSON.stringify(message);
  const jsonBytes = new TextEncoder().encode(json);

  const buffer = new ArrayBuffer(4 + jsonBytes.length);
  const view = new DataView(buffer);
  view.setInt32(0, type);
  new Uint8Array(buffer, 4).set(jsonBytes);

  return buffer;
}

async function getMessage(buf: Blob) : Promise<TypedMessage> {
  if (buf == null || buf == undefined) {
    return {id: 0, message: {id: 0}}
  }
  console.log(typeof buf)
  console.log(buf)
  const dv = new DataView(await buf.arrayBuffer());

  const type = dv.getInt32(0);

  switch (type) {
    case 2:
      return {id: type, message: new QueuedMessage(type)} 
  
    default:
      return {id: 0, message: {id: 0}};
  }
}

export default function HomePage() {
  const navigate = useNavigate();

  const socketUrl = "ws://localhost:8080/ws";

  const {sendMessage, lastMessage} = useWebSocket(socketUrl);


  useEffect(() => {
    const processMessage = async () => {
      if (lastMessage && lastMessage.data instanceof Blob) {
        const parsedMessage = await getMessage(lastMessage.data);
      }
    };
  
    processMessage();
  }, [lastMessage]);


  return (
    <Flex direction="column" m="lg">
      <Group>
        <Button onClick={() => navigate("/login")}>Login</Button>
        <Button onClick={() => navigate("/register")}>Register</Button>
      </Group>
      <h1>WELCOME TO SNEKSI BETTING</h1>
      <Button onClick={() => {sendMessage(getBytes(1, {userId: "5516e359-6c9c-4ebb-a409-52373d536d50"}))}}>Clickme</Button>
    </Flex>
  );
}
