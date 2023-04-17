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
import { useDaumPostcodePopup } from "react-daum-postcode";

function Profile() {
  const [isLoading, setIsLoading] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [profile, setProfile] = useState({});

  // 주소 검색 api
  const open = useDaumPostcodePopup();

  const handleSearchComplete = (data) => {
    let fullAddress = data.address;
    let extraAddress = "";

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress +=
          extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
    }

    console.log(data.zonecode, fullAddress); // e.g. '(12345)서울 성동구 왕십리로2길 20 (성수동1가)'

    setProfile((prevState) => {
      let newState = { ...prevState };
      newState.addressInfo = `(${data.zonecode})${fullAddress}`;
      console.log("프로필 업데이트", newState);

      return newState;
    });
  };

  // 폼을 만들기 위한 여러가지 요소 불러오기
  const { register, handleSubmit, getValues } = useForm();

  // 데이터 전송시 작동할 함수 정의
  const onValid = (data) => {
    const { username, address1, address2 } = getValues();
    console.log("가져온 값", username, address1, address2);
    setProfile((prevState) => {
      let newState = { ...prevState };
      if (username) {
        newState.username = username;
      }
      if (address1) {
        newState.addressInfo = address1;
      }
      if (address2) {
        newState.addressDetails = address2;
      }
      updateInfo(newState);

      return newState;
    });
  };

  const updateInfo = (item) => {
    console.log("profile update request", item);
    call("/api/v1/members", "PATCH", item)
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
    call("/api/v1/members", "GET", null).then((response) => {
      setProfile(response); // update State
      setIsLoading(false);
      console.log(response);
      console.log("get profile from server");
    });
  };

  const deleteAccount = () => {
    const result = window.confirm("정말로 회원 탈퇴를 하시겠습니까?");
    if (result) {
      // 예를 선택한 경우 처리할 코드
      call("/api/v1/members", "DELETE", null).then((response) => {
        alert("회원 탈퇴가 완료되었습니다.");
        window.location.href = "/";
      });
    }
  };

  useEffect(() => {
    getInfo();
  }, []);

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid container spacing={1} style={{ marginBottom: "16px" }}>
        <Typography component="h1" variant="h5">
          Profile
        </Typography>
      </Grid>
      <form noValidate onSubmit={handleSubmit(onValid)}>
        {" "}
        <Grid container spacing={1}>
          <Grid item xs={12} style={{ marginBottom: "16px" }}>
            {!editMode ? (
              <TextField
                variant="outlined"
                required
                fullWidth
                id="username"
                placeholder="이름"
                name="username"
                autoComplete="username"
                disabled
                value={getValues("username") || profile.username}
                {...register("username")}
              />
            ) : (
              <TextField
                variant="outlined"
                required
                fullWidth
                id="username"
                placeholder="이름"
                name="username"
                autoComplete="username"
                defaultValue={profile.username}
                {...register("username")}
              />
            )}
          </Grid>
          <Grid item xs={12} style={{ marginBottom: "16px" }}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="phone"
              placeholder="전화번호"
              name="phone"
              autoComplete="phone"
              disabled
              value={profile.phoneNumber}
            />
          </Grid>
          <Grid container spacing={1}>
            <Grid item xs={10} style={{ marginBottom: "16px" }}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="address1"
                placeholder="주소"
                autoFocus
                disabled
                defaultValue={profile.addressInfo}
                value={getValues("address1") || profile.addressInfo}
                {...register("address1")}
              />
            </Grid>
            <Grid item xs={2}>
              <Button
                className="user-btn"
                type="button"
                onClick={() => {
                  open({ onComplete: handleSearchComplete });
                }}
                fullWidth
                color="primary"
                variant="contained"
                disabled={editMode ? false : true}
              >
                검색
              </Button>
            </Grid>
          </Grid>
          <Grid item xs={12} style={{ marginBottom: "16px" }}>
            {!editMode ? (
              <TextField
                variant="outlined"
                required
                fullWidth
                id="address2"
                placeholder="상세주소"
                name="address2"
                autoComplete="address2"
                disabled
                value={getValues("address2") || profile.addressDetails}
                {...register("address2")}
              />
            ) : (
              <TextField
                variant="outlined"
                required
                fullWidth
                id="address2"
                placeholder="상세주소"
                name="address2"
                autoComplete="address2"
                defaultValue={profile.addressDetails}
                {...register("address2")}
              />
            )}
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
              <div style={{ display: "flex", justifyContent: "flex-end" }}>
                <Button
                  type="button"
                  onClick={() => {
                    setEditMode(true);
                  }}
                  variant="contained"
                  color="primary"
                  style={{ marginRight: "15px" }}
                >
                  수정하기
                </Button>
                <Button
                  type="button"
                  onClick={() => {
                    deleteAccount();
                  }}
                  variant="contained"
                  color="secondary"
                >
                  탈퇴하기
                </Button>
              </div>
            )}
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default Profile;
