import { createBrowserRouter, RouteObject } from "react-router";
import HomePage from "./pages/HomePage/HomePage";
import LoginPage from "./pages/Auth/LoginPage";
import RegisterPage from "./pages/Auth/RegisterPage";
import MainMenu from "./pages/MainMenu/MainMenu";
import PlayGame from "./pages/Game/PlayGame";

export const routes: RouteObject[] = [
  {
    path: "",
    element: <HomePage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/register",
    element: <RegisterPage />,
  },
  {
    path: "/mainmenu",
    element: <MainMenu />,
  },
  {
    path: "/playgame",
    element: <PlayGame />,
  },
];

export const router = createBrowserRouter(routes);
