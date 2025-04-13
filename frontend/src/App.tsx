import { MantineProvider } from "@mantine/core";
import { RouterProvider } from "react-router";
import { router } from "./routes";
import "@mantine/core/styles.css";
import { Notifications } from "@mantine/notifications";

function App() {
  return (
    <MantineProvider>
      <Notifications />
      <RouterProvider router={router} />
    </MantineProvider>
  );
}

export default App;
