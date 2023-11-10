const PORT = 8000
const express = require('express')
const cors = require('cors')
const app = express()
app.use(express.json())
app.use(cors())

const API_KEY = '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'

app.post('/completions', (req, res) => {
  const options = {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${API_KEY}`
        },
        body: JSON.stringify({
            model: "gpt-3.5-turbo",
            messages: [{ role: "user", content: "how are you?"}],
            max_tokens: 150,
        })
    }
    try{
        fetch('https://api.openai.com/v1/chat/completions', options)
    } catch (error) {
        console.log(error)
    } 
})

app.listen(PORT, () => console.log('Your server is running on port PORT'+PORT))