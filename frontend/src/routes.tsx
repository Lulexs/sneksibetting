import { createBrowserRouter, RouteObject } from "react-router";
import HomePage from "./pages/HomePage/HomePage";

export const routes: RouteObject[] = [
  {
    path: "",
    element: <HomePage />,
  },
];

export const router = createBrowserRouter(routes);
