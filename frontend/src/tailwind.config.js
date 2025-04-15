module.exports = {
    content: [
      "./src/**/*.{js,ts,jsx,tsx}",  // ✅ Tailwind will scan these for classes
    ],
    theme: {
      extend: {
        colors: {
          'highlight-1': 'rgb(240, 193, 27)',
        },
      },
    },
    plugins: [],
  }