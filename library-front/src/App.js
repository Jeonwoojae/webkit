import Header from "./layouts/Header";
import Navigator from "./layouts/Navigator";
import Main from "./pages/Main";
import Book from "./pages/Book";
import Search from "./pages/Search";
import Footer from "./layouts/Footer";
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  return (
    <div>
    <BrowserRouter>
      <Header />
      <Navigator />
      <Routes>
        <Route path="/" element={<Main/>}/>
        <Route path="/book/*" element={<Book/>}/>
        <Route path="/book/search/*" element={<Search/>}/>
        <Route path="*" element={<Main/>}/>
      </Routes>
      <Footer />
    </BrowserRouter>
    </div>
  );
}

export default App;
