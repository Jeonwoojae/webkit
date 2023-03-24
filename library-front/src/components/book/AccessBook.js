import React, { useEffect, useState } from "react";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import { MenuValue } from "./MenuValue";
import axios from "axios";
import "./DetailBook.css";

function AccessBook(props) {
  const bookInfo = props.bookInfo;
  const [genreList, setGenreList] = useState([]);
  const [inputs, setInputs] = useState({
    title: bookInfo.title,
    category: bookInfo.category,
    nation: bookInfo.nation,
    genre: bookInfo.genre,
    price: bookInfo.price,
  });

  function findGenre(element) {
    if (element.nation === inputs.nation) return true;
  }

  useEffect(() => {
    const list = MenuValue.find(findGenre);
    if (list != null) setGenreList(list.sub);
  }, [inputs.nation, findGenre]);

  const { title, category, nation, genre, price } = inputs;

  const onChange = (e) => {
    const { value, name } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onPriceChange = (e) => {
    const { value, name } = e.target;
    const onlyNumber = value.replace(/[^0-9]/g, "");
    setInputs({
      ...inputs,
      [name]: onlyNumber,
    });
  };

  function goDetailPage(id) {
    props.setAccessMode(false);
    window.location.replace(`/book/?id=${id}`);
  }

  const onSubmitHandler = () => {
    console.log(inputs);
    if(inputs.nation == undefined || inputs.genre == undefined){
      alert("선택하지 않은 항목이있습니다");
      return 
    }
    if (bookInfo.id == null) {
      axios
        .post(`http://localhost:8080/book`, {
          title: `${inputs.title}`,
          category: `${inputs.category}`,
          nation: `${inputs.nation}`,
          genre: `${inputs.genre}`,
          price: `${inputs.price}`,
        })
        .then((res) => {
          alert("책 생성이 성공했습니다.");
          goDetailPage(res.data.id);
        })
        .catch((e) => {
          alert("책 생성이 실패했습니다.");
        });
    } else {
      axios
        .put(`http://localhost:8080/book/${bookInfo.id}`, {
          title: `${inputs.title}`,
          category: `${inputs.category}`,
          nation: `${inputs.nation}`,
          genre: `${inputs.genre}`,
          price: `${inputs.price}`,
        })
        .then((res) => {
          alert("책 수정이 성공했습니다.");
          goDetailPage(res.data.id);
        })
        .catch((e) => {
          alert("책 수정이 실패했습니다.");
        });
    }
  };

  return (
    <div>
      <ArrowRightIcon fontSize="30px" />
      {bookInfo.id == null ? <>책 생성</> : <>책 수정</>}
      <div>
        <table className="detail-table">
          <tbody>
            <tr>
              <td className="detail-table-td-first">제목</td>
              <td className="detail-table-td-second">
                <input
                  className="detail-input"
                  name="title"
                  onChange={onChange}
                  value={title}
                />
              </td>
            </tr>
            <tr>
              <td className="detail-table-td-first">카테고리</td>
              <td className="detail-table-td-second">
                <input
                  className="detail-input"
                  name="category"
                  onChange={onChange}
                  value={category}
                />
              </td>
            </tr>
            <tr>
              <td className="detail-table-td-first">국가</td>
              <td className="detail-table-td-second">
                <select
                  onChange={onChange}
                  name="nation"
                >
                  <option value="" disabled selected>
                    국가를 선택하세요
                  </option>
                  {MenuValue.map((item) => (
                    <option key={item.nation} value={item.nation}>
                      {item.nation}
                    </option>
                  ))}
                </select>
              </td>
            </tr>
            <tr>
              <td className="detail-table-td-first">장르</td>
              <td className="detail-table-td-second">
                <select onChange={onChange} name="genre">
                <option value="" disabled selected>
                    장르를 선택하세요
                  </option>
                  {genreList.map((item) => (
                    <option key={item.genre} value={item.genre}>
                      {item.genre}
                    </option>
                  ))}
                </select>
              </td>
            </tr>
            <tr>
              <td className="detail-table-td-first">가격</td>
              <td className="detail-table-td-second">
                <input
                  className="detail-input"
                  name="price"
                  onChange={onPriceChange}
                  value={price}
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <button className="detail-button" onClick={onSubmitHandler}>
        저장
      </button>
    </div>
  );
}

export default AccessBook;
