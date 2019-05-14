import http from "./httpService";
import { apiUrl } from "../config.json";


export function getUsers(name) {
  return http.get(`${apiUrl}/users${name ? '?name=' + name : ''}`);
}

export function getUser(id) {
  return http.get(`${apiUrl}/users/${id}`);
}

export function addSkill(id, data) {
  return http.post(`${apiUrl}/users/${id}/add_skill`, data);
}

export function deleteSkill(id, data) {
  return http.post(`${apiUrl}/users/${id}/delete_skill`, data);
}

export function endorse(id, data) {
    return http.post(`${apiUrl}/users/${id}/endorse`, data);
}

export function registerUser(data) {
    return http.post(`${apiUrl}/sign-up`, data);
}
