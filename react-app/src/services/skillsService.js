import http from "./httpService";

export function getSkills() {
  return http.get(`/skills`, {
    headers: {
      "Authorization": localStorage.getItem('jwtToken')
    }
  });
}
