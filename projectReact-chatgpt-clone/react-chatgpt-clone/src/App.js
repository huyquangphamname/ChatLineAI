import { useState, useEffect} from 'react'
const App = () => {
  const [ value, setValue ] = useState("")
  const [ message, setMessage ] = useState(null)
  const [ previousChats, setPreviousChats ] = useState([])
  const [ currentTitle, setCurrentTitle ] = useState(null)

  const createNewChat = () => {
    setMessage(null)
    setValue("")
    setCurrentTitle(null)
  }
  /**
   * Fetches messages from the server.
   * 
   * This function sends a POST request to the server with the user's message.
   * The server processes the message and returns a response.
   * The response is then set as the messages state.
   * 
   * @returns {Promise<void>} A promise that resolves when the messages are fetched.
   */
  const handleClick = (uniqueTitle) => {
    setCurrentTitle(uniqueTitle)
  }

  const getMessages = async () => {
    const options = {
      method: "POST",
      body: JSON.stringify({
        message: value
      }),
      headers: {
        "Content-Type": "application/json",
      }
    }
    try {
      const response = await fetch('http://localhost:8000/completions', options)
      const data = await response.json()
      console.log(data)
      setMessage(data.choices[0].message)
    } catch (error) {
        console.error(error)
    }


  }

  useEffect(() => {
    console.log(currentTitle, value, message)
    if (!currentTitle && value && message) {
      setCurrentTitle(value)
    }
    if (currentTitle && value && message) {
      setPreviousMessages(prevChats => (
        [...prevChats,
          {
            title: currentTitle,
            role: "user",
            content: value
          },
          {
            title: currentTitle,
            role: message.role,
            content: message.content
          }
        ]
      ))
    }
  }, [message, currentTitle])

    console.log(previousChats)

    const currentChat = previousChats.filter(previousChat => previousChat.title === currentTitle)
    const uniqueTitle = Array.from(new Set(previousChats.map(previousChat => previousChat.title)))
    console.log(uniqueTitle)

  return (
    <div className="app">
      <section className="side-bar">
        <button onClick={createNewChat}>+ New chat</button>
        <ul className="history">
          {uniqueTitle?.map((uniqueTitle, index) => <li key={index} onClick={()=>handleClick(uniqueTitle)}> {uniqueTitle}</li>)}
        </ul>
        <nav>
          <p>Make by App</p>
        </nav>
      </section>
        
      <section className="main">
        {!currentTitle && <h1>AniaGPT</h1>}
        <ul className="feed">
            {currentChat.map((chatMessage, index) => <li key={index}> 
              <p className="role">{chatMessage.role}</p>
              <p>{chatMessage.message}</p>
            </li>)}
        </ul>
        <div className="bottom-section">
          <div className="input-container">
            <label htmlFor="myInput">Input:</label>
            <input
              type="text"
              id="myInput"
              name="myInput"
              value = {value} 
              onChange = {(e) => setValue(e.target.value)}
            />
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
