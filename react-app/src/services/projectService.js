import http from "./httpService";

export function getProjects(search) {
    return http.get(`/projects${search ? '?search=' + search : ''}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function getProject(id) {
    return http.get(`/projects/${id}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function addBid(id, data) {
    return http.put(`/projects/${id}`, data, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}
