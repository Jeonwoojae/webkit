import {
  Button,
  Container,
  Grid,
  TextField,
  Typography,
} from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import call from "../service/ApiService";

function Profile() {
  const [isLoading, setIsLoading] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [profile, setProfile] = useState({ email: "", username: "" });

  // 폼을 만들기 위한 여러가지 요소 불러오기
  const { register, handleSubmit, getValues } = useForm();

  // 데이터 전송시 작동할 함수 정의
  const onValid = (data) => {
    console.log(data);

    const { username, email } = getValues();
    setProfile((prevState) => {
      let newState = { ...prevState };
      newState.username = username;
      newState.email = email;
      //console.log(newState.done);
      updateInfo(newState);

      return newState;
    });
  };

  const updateInfo = (item) => {
    console.log("profile update request", item);
    call("/auth/profile", "PUT", item)
      .then((response) => {
        console.log("profile update success");
        console.log("모드 변경");
        setEditMode(false);
      })
      .then(() => {
        getInfo();
      });
  };

  const getInfo = () => {
    call("/auth/profile", "GET", null).then((response) => {
      setProfile(response); // update State
      setIsLoading(false);
      console.log(response);
      console.log("get profile from server");
    });
  };

  useEffect(() => {
    getInfo();
  }, []);

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid container spacing={2}>
        <Typography component="h1" variant="h5">
          Profile
        </Typography>
      </Grid>
      <form noValidate onSubmit={handleSubmit(onValid)}>
        {" "}
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="username"
              label="이름"
              name="username"
              autoComplete="username"
              disabled={editMode ? false : true}
              defaultValue={"profile.username"}
              {...register("username")}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="phone"
              label="전화번호"
              name="phone"
              autoComplete="phone"
              disabled
              defaultValue={"profile.phone"}
              {...register("phone")}
            />
          </Grid>
          <Grid container spacing={12}>
            <Grid item xs={10}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="address"
                label="주소"
                autoFocus
                disabled
                {...register("address1")}
              />
            </Grid>
            <Grid item xs={2}>
              <div
                className="user-btn"
                type="button"
                onClick={() => console.log("검색 API로 검색")}
                fullWidth
              >
                검색
              </div>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="address2"
              label="상세주소"
              name="address2"
              autoComplete="address2"
              disabled={editMode ? false : true}
              defaultValue={"profile.address2"}
              {...register("address2")}
            />
          </Grid>
          <Grid item xs={12}>
            {editMode ? (
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
              >
                수정완료
              </Button>
            ) : (
              <>
                <button
                  type="button"
                  onClick={() => {
                    setEditMode(true);
                  }}
                  fullWidth
                  variant="contained"
                  color="primary"
                >
                  수정하기
                </button>
                <button
                  type="button"
                  onClick={() => {
                    alert("정말 탈퇴하시겠습니까?");
                  }}
                  fullWidth
                  variant="contained"
                  color="secondary"
                >
                  탈퇴하기
                </button>
              </>
            )}
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default Profile;
