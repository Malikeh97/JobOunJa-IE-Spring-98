import http from "./httpService";

export function registerUser(data) {
    return http.post(`/sign-up`, data);
}