import { createBrowserRouter, RouteObject } from "react-router";
import HomePage from "./pages/HomePage/HomePage";
import LoginPage from "./pages/Auth/LoginPage";
import RegisterPage from "./pages/Auth/RegisterPage";
import MainMenu from "./pages/MainMenu/MainMenu";

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
];

export const router = createBrowserRouter(routes);
