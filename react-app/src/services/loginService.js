import http from "./httpService";

export function loginUser(data) {
    return http.post(`/login`, data);
}