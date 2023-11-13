const App = () => {
  const [ value, setValue ] = useState(null)
  const getMessages = async () => {
    const options ={
      method: "POST",
      body: JSON.stringify({
        message: "Hello, how are you?"
      }),
      headers: {
        "Content-Type": "application/json",
      }
    }
    try {
      const response = await fetch('http://localhost:8000/completions', options)
      const data = await response.json()
      console.log(data)
    } catch (error) {
        console.error(error)
    }


  }

  console.log(value)

  return (
    <div className="app">
      <section className="side-bar">
        <button>+ New chat</button>
        <ul className="history">
          <li> BBBBBBB</li>
        </ul>
        <nav>
          <p>Make by App</p>
          </nav>
      </section>
        
      <section className="main">
        <h1>AniaGPT</h1>
        <ul className="feed">
          
        </ul>
        <div className="bottom-section">
          <div className="input-container">
            <input value = {value} onChange = {(e) => setValue(e.target.value)}/>
            <div id="submit" onClick={getMessages}>➢</div>
          </div>
          <p className="info">
            ChatGPT is a chatbot that uses the GPT-3 API to generate responses to your messages.
            Our goal is to make AI systems more natural and safe to interact with.
            Your feedback is greatly appreciated!
          </p>
        </div>
      </section>
      </div>
  );
}

export default App;
