import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import AccessBook from '../components/book/AccessBook';
import DetailBook from '../components/book/DetailBook'

function Book() {
  const [accessMode, setAccessMode] = useState(false);
  const [bookInfo, setBookInfo] = useState({});
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  var bookId = params.get("id");
  console.log(bookId);
  
  useEffect(() => {
    if(bookId != null)
    {axios
      .get(`http://localhost:8080/book/${bookId}`)
      .then((res) => {
        setBookInfo(res.data);
      });}
      console.log(accessMode);
  }, [accessMode, bookId]);

  return (
    <div>
        {accessMode || bookInfo.id==null ?(<AccessBook setAccessMode={setAccessMode} bookInfo={bookInfo} />):(<DetailBook bookInfo={bookInfo} setAccessMode={setAccessMode}/>)}
    </div>
  )
}

export default Book