var gapi = window.gapi;
/* 
    Update with your own Client Id and Api key 
  */
var CLIENT_ID =
  "110595471665-u9uafjpat6aml6fd1pc3lp6o2ihdmtv2.apps.googleusercontent.com";
var API_KEY = "AIzaSyCp6EMx8a4T9U_dxtvedasM63MrSPAtVh0";

var DISCOVERY_DOCS = [
  "https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest",
];
var SCOPES = "https://www.googleapis.com/auth/calendar.events";
export const AddEventGoogleWithMeet = (
  Reciever1,
  Reciever2,
  dateDebut,
  dateFin,
  summary,
  location,
  description
) => {
  gapi.load("client:auth2", () => {
    gapi.client.init({
      apiKey: API_KEY,
      clientId: CLIENT_ID,
      discoveryDocs: DISCOVERY_DOCS,
      scope: SCOPES,
    });

    gapi.client.load("calendar", "v3", () => console.log("bam!"));

    gapi.auth2
      .getAuthInstance()
      .signIn()
      .then(() => {
        var event = {
          summary: summary,
          location: location,
          description: description,
          start: {
            dateTime: dateDebut.toISOString(),
          },
          end: {
            dateTime: dateFin.toISOString(),
          },
          attendees: [{ email: Reciever2 }, { email: Reciever1 }],
          reminders: {
            useDefault: false,
            overrides: [
              { method: "email", minutes: 24 * 60 },
              { method: "popup", minutes: 10 },
            ],
          },

          conferenceData: {
            createRequest: {
              requestId: Math.random().toString(36).substr(2, 5),
              conferenceSolutionKey: "hangoutsMeet",
            },
            status: {
              statusCode: "success",
            },
          },
        };

        var request = gapi.client.calendar.events.insert({
          calendarId: "primary",
          resource: event,
          conferenceDataVersion: 1,
          sendUpdates: "all",
        });

        request.execute((event) => {
          window.open(event.htmlLink);
        });
      });
  });
};
export const AddEventGoogle = (
  Reciever1,
  Reciever2,
  dateDebut,
  dateFin,
  summary,
  location,
  description
) => {
  gapi.load("client:auth2", () => {
    gapi.client.init({
      apiKey: API_KEY,
      clientId: CLIENT_ID,
      discoveryDocs: DISCOVERY_DOCS,
      scope: SCOPES,
    });

    gapi.client.load("calendar", "v3", () => console.log("bam!"));

    gapi.auth2
      .getAuthInstance()
      .signIn()
      .then(() => {
        var event = {
          summary: summary,
          location: location,
          description: description,
          start: {
            dateTime: dateDebut.toISOString(),
          },
          end: {
            dateTime: dateFin.toISOString(),
          },
          attendees: [{ email: Reciever2 }, { email: Reciever1 }],
          reminders: {
            useDefault: false,
            overrides: [
              { method: "email", minutes: 24 * 60 },
              { method: "popup", minutes: 10 },
            ],
          },
        };

        var request = gapi.client.calendar.events.insert({
          calendarId: "primary",
          resource: event,
          sendUpdates: "all",
        });

        request.execute((event) => {
          window.open(event.htmlLink);
        });
      });
  });
};
