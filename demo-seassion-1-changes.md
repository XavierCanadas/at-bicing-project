# Demo session 1

To implement the new functionality we made the following changes:
1. We added the new "name" parameter to the request body of the POST ./clients (ClientResource.java, SubscribeRequest.java, Client.java)
2. We added the new field to the New client form and we updated the client.js to add the name to the request (NewClient.jsp, clients.js)
3. To implement the request to the OpenGateway, we updated the OpenGatewayService. First, we created a new app in the OpenGateway portal with type Know Your Customer. 
4. Then, we added the constants with the url, client id and client secret.
5. After that, we updated the current code that we had to get the auth and token for the age verify. We adapted the code to support the new match request.
6. Finally, we created a new isCorrectName(). It requests the auth code, requests the token and finally makes the request to the match endpoint with body phone number and name


The AI tools that we used are:
- GitHub Copilot: We asked to generate trivial code that we already know and also to fix errors in the client.
- Google Gemini: To correct an "internal error (500)" that we were obtaining due to the jsonPayload being constructed wrong. Also to know which example names the mock mode accepts and which does not.

prompts examples:
- "in internationalPhone, check if it has the country code and if not add +34"
- "I added a new field to the NewClientForm. Now its not passing the value to the post endpoint"
- "En el mock mode de la API del nom, quins noms els dona com a valids i quins no?"
- "I'm using the OpenGateway api by telefonica to verify if a phone matches with the name the user provides.
  https://developers.opengateway.telefonica.com/reference/kyc_match_v02-2

I'm using the api in mock mode. Search the logic of returning a match or not"