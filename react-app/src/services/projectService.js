import http from "./httpService";
import { apiUrl } from "../config.json";

export function getProjects() {
  return http.get(`${apiUrl}/projects`);
}

export function getProject(id) {
    return http.get(`${apiUrl}/projects/${id}`);
}

export function addBid(id, data) {
    return http.post(`${apiUrl}/projects/${id}/add_bid`, data);
}
