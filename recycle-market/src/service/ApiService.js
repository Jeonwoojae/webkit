import { API_BASE_URL } from "../app-config";
const ACCESS_TOKEN = "ACCESS_TOKEN";

function call(api, method, params) {
  let headers = new Headers({
    "Content-Type": "application/json",
  });
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  if (accessToken) {
    headers.append("Authorization", "Bearer " + accessToken);
  }

  let url = API_BASE_URL + api;

  // GET 방식일 경우 URL 쿼리 스트링에 파라미터를 추가
  if (method.toUpperCase() === 'GET' && params) {
    const queryString = Object.entries(params).map(([key, val]) => `${key}=${val}`).join('&');
    url += `?${queryString}`;
  }

  let options = {
    headers: headers,
    method: method,
  };

  // POST 방식일 경우 요청 바디에 파라미터를 추가
  if (method.toUpperCase() === 'POST' && params) {
    options.body = JSON.stringify(params);
  }

  return fetch(url, options)
    .then((response) =>
      response.json().then((json) => {
        if (!response.ok) {
          return Promise.reject(json);
        }
        return json;
      })
    )
    .catch((error) => {
      console.log(error.error);
      if (error.status === 403) {
        alert(error.error);
        window.location.href = "/";
      }
      return Promise.reject(error);
    });
}

export function postFormData(api, formData) {
  const headers = new Headers({
    Authorization: `Bearer ${localStorage.getItem("ACCESS_TOKEN")}`,
  });

  const options = {
    method: "POST",
    headers,
    body: formData,
  };

  return fetch(API_BASE_URL + api, options)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .catch((error) => {
      console.error("There was a problem with the network request:", error);
    });
}


// 로그인을 위한 API 서비스 메소드 signin
export function signin(userDto) {
    return call("/login","POST",userDto)
    .then((response) => {
      console.log(response);
        if(response.atk) {
            // local 스토리지에 토큰 저장
            localStorage.setItem("ACCESS_TOKEN", response.atk);
            localStorage.setItem("ROLE",response.role);
            // token이 존재하는 경우 todo 화면으로 리다이렉트
            window.location.href="/";
        }
    })
}

// 회원가입 요청
export function signup(userDto) {
    return call("/api/v1/members","POST",userDto)
    .then((response) => {
        if(response.id) {
            window.location.href="/";
        }
    })
    .catch((error) => {
        console.log(error.status);
        if(error.status === 403){
            window.location.href = "/auth/signup"
        }
        return Promise.reject(error);
    })
}

// 로그아웃
export function signout() {
    // local 스토리지에 토큰 삭제
    localStorage.removeItem("ACCESS_TOKEN");
    localStorage.removeItem("ROLE");
    window.location.href = "/";
}

// 인증코드 메일로 보내기
export function sendEmail(email) {
  console.log(`/mail/send?email=${email}`);
  return call(`/mail/send?email=${email}`,"POST",null)
    .then((response) => {
        alert("메일로 전송했습니다. 메일함을 확인해주세요.")
    })
    .catch((error)=>{
      alert("메일 보내기에 실패했습니다.");
      console.log(error);
    })
}

// 인증코드 검증하기
export function checkCertificateCode(code) {
  console.log(`/mail/check?code=${code}`);
  return call(`/mail/check?code=${code}`, "GET", null)
  .then((response) => {
      console.log(response,"인증코드 확인 성공");
  })
  .catch((error) => {
      console.log(error.status);
      if(error.status === 400){
          alert("인증코드 검증이 실패했습니다.");
      }
  });
}

export default call;
