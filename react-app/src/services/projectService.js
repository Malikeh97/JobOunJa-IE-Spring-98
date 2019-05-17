import http from "./httpService";
import {apiUrl} from "../config.json";

export function getProjects(search) {
    return http.get(`${apiUrl}/projects${search ? '?search=' + search : ''}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function getProject(id) {
    return http.get(`${apiUrl}/projects/${id}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function addBid(id, data) {
    return http.post(`${apiUrl}/projects/${id}/add_bid`, data, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}
