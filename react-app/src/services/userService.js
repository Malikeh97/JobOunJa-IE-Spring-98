import http from "./httpService";


export function getUsers(name) {
    return http.get(`/users${name ? '?name=' + name : ''}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function getUser(name) {
    return http.get(`/users/${name}`, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function addSkill(id, data) {
    return http.put(`/users`, data, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function deleteSkill(id, data) {
    return http.delete(`/users`, {
        data,
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}

export function endorse(id, data) {
    return http.put(`/users/${id}`, data, {
        headers: {
            "Authorization": localStorage.getItem('jwtToken')
        }
    });
}
