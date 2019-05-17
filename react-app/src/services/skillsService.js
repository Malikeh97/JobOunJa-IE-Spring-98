import http from "./httpService";
import { apiUrl } from "../config.json";

export function getSkills() {
  return http.get(`${apiUrl}/skills`, {
    headers: {
      "Authorization": localStorage.getItem('jwtToken')
    }
  });
}
