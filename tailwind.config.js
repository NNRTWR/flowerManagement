/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/templates/*.{html,js}","./src/main/resources/templates/*/*.{html,js}","./src/main/resources/static/*/*.{html,js}"],
  theme: {
    extend: {
      fontFamily:{
        body:['Kanit', 'sans-serif']
      }
    },
  },
  // daisyui: {
  //   themes: [
  //     {
  //       mytheme: {
  //         "primary": "#0e8e48",
  //         "secondary": "#d3286f",
  //         "accent": "#9af9a6",
  //         "neutral": "#14181f",
  //         "base-100": "#2d3343",
  //         "info": "#69a9f2",
  //         "success": "#1e7660",
  //         "warning": "#f2b069",
  //         "error": "#f76e77",
  //       },
  //     },
  //   ],
  // },
  plugins: [require("daisyui")],
}

