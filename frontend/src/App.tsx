import { MantineProvider } from "@mantine/core";
import { RouterProvider } from "react-router";
import { router } from "./routes";

function App() {
  return (
    <MantineProvider>
      <RouterProvider router={router} />
    </MantineProvider>
  );
}

export default App;
