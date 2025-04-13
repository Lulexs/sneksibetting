import { Button, Flex, PasswordInput, TextInput, Title } from "@mantine/core";
import { hasLength, useForm } from "@mantine/form";
import { useStore } from "../../stores/store";

export default function RegisterPage() {
  const form = useForm({
    mode: "uncontrolled",
    initialValues: { username: "", password: "" },
    validate: {
      username: hasLength(
        { min: 3, max: 15 },
        "Must be at least 3 characters and at most 15 characters"
      ),
      password: hasLength({ min: 6 }, "Must be at least 6 characters"),
    },
  });

  const { userStore } = useStore();

  return (
    <Flex direction="column" m="lg" h="85vh">
      <Title>Register</Title>
      <form
        style={{ width: "300px" }}
        onSubmit={form.onSubmit((values) => {
          userStore.register(values);
        })}
      >
        <TextInput
          {...form.getInputProps("username")}
          key={form.key("username")}
          label={"Username"}
          m="lg"
        />
        <PasswordInput
          {...form.getInputProps("password")}
          key={form.key("password")}
          label={"Password"}
          m="lg"
        />
        <Button m="lg" type="submit">
          Register
        </Button>
      </form>
    </Flex>
  );
}
