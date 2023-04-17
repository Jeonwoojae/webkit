export default function parsingDate(date) {
  const input = new Date(date);
  const formattedDate = input.toLocaleString("ko-KR", {
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
  });
  return formattedDate;
}

