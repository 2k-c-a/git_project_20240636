// ===== 로또 번호 생성기 =====

// 화면 요소 가져오기
const ballsBox = document.getElementById("balls");     // 공이 표시될 영역
const countText = document.getElementById("count");    // 생성 횟수 글자
const drawBtn = document.getElementById("drawBtn");    // 번호 뽑기 버튼
const resetBtn = document.getElementById("resetBtn");  // 초기화 버튼

// 번호를 뽑은 횟수
let drawCount = 0;

// 번호에 맞는 공 색상 클래스 이름을 돌려주는 함수
function getBallColor(number) {
    if (number <= 10) return "ball-yellow";
    if (number <= 20) return "ball-blue";
    if (number <= 30) return "ball-red";
    if (number <= 40) return "ball-gray";
    return "ball-green";
}

// 번호 하나로 공 모양 요소를 만들어 돌려주는 함수
function makeBall(number) {
    const ball = document.createElement("div");
    ball.className = "ball " + getBallColor(number);
    ball.textContent = number;
    return ball;
}

// 1~45 중 중복 없는 번호 6개를 뽑아 오름차순으로 돌려주는 함수
function pickNumbers() {
    const numbers = [];  // 뽑힌 번호를 담을 배열

    // 6개가 모일 때까지 반복
    while (numbers.length < 6) {
        const n = Math.floor(Math.random() * 45) + 1;  // 1~45 사이 숫자
        // 이미 뽑은 번호가 아니면 추가
        if (!numbers.includes(n)) {
            numbers.push(n);
        }
    }

    // 작은 수부터 큰 수 순서로 정렬
    numbers.sort(function (a, b) {
        return a - b;
    });
    return numbers;
}

// 번호 뽑기 버튼을 눌렀을 때 실행되는 함수
function draw() {
    const numbers = pickNumbers();

    // 이전 공을 지우고 새 공 6개를 그린다
    ballsBox.innerHTML = "";
    numbers.forEach(function (n) {
        ballsBox.appendChild(makeBall(n));
    });

    // 생성 횟수 1 증가 후 화면에 표시
    drawCount++;
    countText.textContent = drawCount;
}

// 초기화 버튼을 눌렀을 때 처음 화면으로 되돌리는 함수
function reset() {
    ballsBox.innerHTML = "";
    drawCount = 0;
    countText.textContent = drawCount;
}

// 버튼에 클릭 이벤트 연결
drawBtn.addEventListener("click", draw);
resetBtn.addEventListener("click", reset);


// ===== 최근 400회 번호별 출현 횟수 그래프 =====

// 분석 범위 (동행복권 공식 당첨 결과 기준, 보너스 번호 제외)
const STATS_FROM = 849;   // 시작 회차
const STATS_TO = 1248;    // 마지막 회차 (2026-10-31 추첨)

// 1~45번 각 번호가 나온 횟수 (미리 한 번 계산해 둔 값)
// 배열의 0번째 칸이 1번, 44번째 칸이 45번의 횟수
const STATS_COUNTS = [
    49, 46, 61, 49, 36, 59, 57, 43, 49, 43,  //  1~10
    49, 57, 64, 52, 55, 64, 51, 61, 54, 50,  // 11~20
    56, 58, 52, 56, 44, 52, 58, 57, 51, 54,  // 21~30
    55, 52, 55, 55, 59, 53, 57, 65, 54, 49,  // 31~40
    48, 53, 46, 57, 55                       // 41~45
];

// 막대 색상 (공 색상과 같게 맞춤)
const BAR_COLORS = {
    "ball-yellow": "#fbc400",
    "ball-blue": "#69c8f2",
    "ball-red": "#ff7272",
    "ball-gray": "#aaaaaa",
    "ball-green": "#b0d840"
};

// 저장된 횟수로 그래프를 그리는 함수
function drawChart() {
    const chart = document.getElementById("chart");
    const info = document.getElementById("statsInfo");

    const max = Math.max(...STATS_COUNTS);  // 가장 많이 나온 횟수
    const min = Math.min(...STATS_COUNTS);  // 가장 적게 나온 횟수

    // 설명 문구 표시
    info.textContent = STATS_FROM + "회 ~ " + STATS_TO + "회 기준 (보너스 번호 제외) · "
        + "최다 " + max + "회 / 최소 " + min + "회";

    // 1번부터 45번까지 한 줄씩 만든다
    STATS_COUNTS.forEach(function (times, index) {
        const number = index + 1;

        const row = document.createElement("div");  // 한 줄 전체
        row.className = "chart-row";

        const track = document.createElement("div");  // 막대 바탕
        track.className = "bar-track";

        const bar = document.createElement("div");  // 실제 막대
        bar.className = "bar";
        bar.style.width = (times / max * 100) + "%";  // 최다 횟수를 100%로 잡는다
        bar.style.backgroundColor = BAR_COLORS[getBallColor(number)];
        track.appendChild(bar);

        const label = document.createElement("span");  // 횟수 글자
        label.className = "bar-count";
        if (times === max) label.className += " most";
        if (times === min) label.className += " least";
        label.textContent = times + "회";

        row.appendChild(makeBall(number));
        row.appendChild(track);
        row.appendChild(label);
        chart.appendChild(row);
    });
}

// 페이지가 열리면 그래프를 그린다
drawChart();
