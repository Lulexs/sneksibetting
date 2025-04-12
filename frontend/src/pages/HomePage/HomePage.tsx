import { Button, Flex, Group } from "@mantine/core";

export default function HomePage() {
  return (
    <Flex>
      <Group>
        <Button>Login</Button>
        <Button>Register</Button>
      </Group>
      <h1>WELCOME TO SNEKSI BETTING</h1>
    </Flex>
  );
}
