import "dotenv/config";
import OpenAI from "openai";

const openai = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY
});

export const parseRoomSearch = async (message) => {
  const response = await openai.responses.create({
    model: "gpt-5.6-luna",

    instructions: `
You are RoomEase AI.

Your job is to understand a user's hotel-room search request.

Extract the following information:

- roomType
- minPrice
- maxPrice
- checkIn
- checkOut
- guests
- preferences

Rules:

- Return null when information is not provided.
- Do not invent information.
- Dates must use YYYY-MM-DD format when the user provides a date.
- Prices must be numbers.
- guests must be a number.
- preferences should be an array of strings.

Today's date is ${new Date().toISOString().split("T")[0]}.

Return ONLY valid JSON.
`,

    input: message
  });

  return response.output_text;
};