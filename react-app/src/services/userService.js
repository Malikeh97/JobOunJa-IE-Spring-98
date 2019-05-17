import http from "./httpService";
import { apiUrl } from "../config.json";


export function getUsers(name) {
  return http.get(`${apiUrl}/users${name ? '?name=' + name : ''}`, {
      headers: {
          "Authorization": localStorage.getItem('jwtToken')
      }
  });
}

export function getUser(id) {
  return http.get(`${apiUrl}/users/${id}`, {
      headers: {
          "Authorization": localStorage.getItem('jwtToken')
      }
  });
}

export function addSkill(id, data) {
  return http.put(`${apiUrl}/users/add_skill`, data, {
      headers: {
          "Authorization": localStorage.getItem('jwtToken')
      }
  });
}

export function deleteSkill(id, data) {
  return http.delete(`${apiUrl}/users/delete_skill`, data, {
      headers: {
          "Authorization": localStorage.getItem('jwtToken')
      }
  });
}

export function endorse(id, data) {
    return http.put(`${apiUrl}/users/${id}/endorse`, data, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}
