import http from "./httpService";
import {apiUrl} from "../config.json";


export function getUsers(name) {
    return http.get(`${apiUrl}/users${name ? '?name=' + name : ''}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function getUser(name) {
    return http.get(`${apiUrl}/users/${name}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function addSkill(id, data) {
    return http.put(`${apiUrl}/users`, data, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function deleteSkill(id, data) {
    return http.delete(`${apiUrl}/users`, {
        data,
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function endorse(id, data) {
    return http.put(`${apiUrl}/users/${id}`, data, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}
