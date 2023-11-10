const App = () => {
  return (
    <div className="app">
      <section className="side-bar">
        <button>+ New chat</button>
        <ul className="history"></ul>
        <nav>
          <p>Make by App</p>
          </nav>
      </section>
        
      <section className="main">
        <h1>ChatGPT</h1>
        <ul className="feed">


        </ul>
        <div className="bottom-section">
          <div className="input-container">
            <input/>
            <div id="submit">➢</div>
          </div>
          <p className="Info">
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
