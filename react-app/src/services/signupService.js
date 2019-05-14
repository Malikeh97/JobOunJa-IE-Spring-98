import http from "./httpService";
import {apiUrl} from "../config";

export function registerUser(data) {
    return http.post(`${apiUrl}/sign-up`, data);
}