import http from "./httpService";
import {apiUrl} from "../config";

export function loginUser(data) {
    return http.post(`${apiUrl}/sign-up`, data);
}