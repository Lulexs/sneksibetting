import { makeAutoObservable, runInAction } from "mobx";
import { User, UserCreds } from "../models/User";
import axios from "axios";
import { router } from "../routes";
import { notifications } from "@mantine/notifications";
import { makePersistable } from "mobx-persist-store";

export default class UserStore {
  user: User | null = null;

  constructor() {
    makeAutoObservable(this);

    makePersistable(this, {
      name: "UserStore",
      properties: ["user"],
      storage: window.localStorage,
    });
  }

  login = async (creds: UserCreds) => {
    try {
      const user = (
        await axios.post("http://localhost:8080/users/login", { ...creds })
      ).data;
      runInAction(() => (this.user = user));
      router.navigate("/mainmenu");
    } catch (error) {
      notifications.show({
        title: "Error logging in",
        message: "Wrong username or password",
        color: "red",
      });
    }
  };

  register = async (creds: UserCreds) => {
    try {
      const user = (
        await axios.post("http://localhost:8080/users/register", { ...creds })
      ).data;
      runInAction(() => (this.user = user));
      router.navigate("/mainmenu");
    } catch (error) {
      notifications.show({
        title: "Error logging in",
        message: "Username probably taken",
        color: "red",
      });
    }
  };
}
