import Aboutus from "./component/Aboutus";
import Contact from "./component/Contact";
import Header from "./component/Header";
import Lecture from "./component/Lecture";
import Main from "./component/Main";
import Product from "./component/Product";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Header></Header>
        <Routes>
          <Route path="/" element={<Main />}></Route>
          <Route path="/aboutus" element={<Aboutus />}></Route>
          <Route path="/product" element={<Product />}></Route>
          <Route path="/lecture" element={<Lecture />}></Route>
          <Route path="/contact" element={<Contact />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
