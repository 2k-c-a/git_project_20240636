# 알고리즘 실행 시각화 웹사이트 (가칭)

알고리즘 코드를 한 단계씩 따라가며, 그 단계가 자료구조의 상태를 어떻게 바꾸는지 한국어 설명과 함께 보여주는 학습용 웹사이트입니다. 모바일 웹 수업 실습 프로젝트입니다.

이 문서는 초안이며 구현 상태에 맞춰 갱신합니다. 설계는 [DESIGN.md](DESIGN.md), 단계별 계획은 [IMPLEMENTATION_PLAN.md](IMPLEMENTATION_PLAN.md), 인터페이스 계약과 검증 기준은 [docs/CONTRACTS.md](docs/CONTRACTS.md)와 [docs/ACCEPTANCE.md](docs/ACCEPTANCE.md)에 있습니다.

## 현재 상태

| 항목 | 상태 |
|---|---|
| 로컬 서버(정적 웹, `/api/health`) | 동작, 자동 테스트 있음 |
| 실행 기록(trace) 형식·검증기·로더, 4개 알고리즘(BFS, DFS, 이분 탐색, 버블 정렬) × 예제 16개 × Python·C++·Java·Kotlin·Rust·C·JavaScript 코드 | 있음, 자동 테스트 있음 (`npm run validate:traces`) |
| 시각화 화면: 알고리즘·예제·언어 선택, 한 단계씩(다음·이전·처음으로) 보기, 자동 재생(0.5·1·2배속, 마지막 단계에서 멈춤), 단계 슬라이더. 언어를 바꿔도 단계·상태 유지, 알고리즘·예제를 바꾸면 처음부터 | 있음 (`/web/html/visualizer.html`), 자동 테스트와 브라우저 확인 |
| DB: 프로필·게시글 테이블, 제약, RLS, 동시 수정 방지 RPC (`supabase/migrations`) | SQL과 테스트 있음 (`npm run test:db`, 로컬 PostgreSQL 임시 클러스터에서 확인). **실제 프로젝트 적용 및 RLS·정책 개수·RPC 실행 방식 확인(2026-10-16). 실제 계정 권한 통합 검증은 남음** |
| 공개 설정·사용자 인증·프로필 API (`/api/config`, `/api/me`, `/api/me/profile`) | 있음, 자동 테스트 있음(인증·DB 경계는 가짜 Supabase). **실제 Supabase 프로젝트로 하는 통합 검증은 아직 하지 않음** |
| 회원가입·로그인·게시판 화면, 게시판 API | 미구현 (Supabase 설정 필요) |
| 공통 메뉴·GitHub 링크·장안대학교 지도가 적용된 페이지(홈, 소개, 아이디어, 지도, 갤러리, 문의) | 있음, 자동 테스트 있음 (갤러리·문의는 수업 실습용) |

## 폴더

```text
mobile/web/
├── frontend/   html, css, js, img, audio (브라우저에 공개되는 파일)
├── backend/    FastAPI + Uvicorn 서버, 테스트 (README 참고)
├── docs/       계약과 검증 기준
├── supabase/   DB migration(SQL)과 테스트, 적용 방법 (supabase/README.md)
├── scripts/    예제 데이터 생성(generate-traces)·검증(validate-traces)
├── package.json   프런트엔드 테스트 실행 설정 (빌드 도구 없음)
├── DESIGN.md, IMPLEMENTATION_PLAN.md
```

## 준비물

- Python 3.12
- PostgreSQL (DB 테스트용, 선택. `initdb`·`postgres`·`psql`이 필요하며 PostgreSQL 18.3(Windows)에서만 실행해 봤습니다. 없으면 DB 테스트는 건너뛰어집니다. 설치하지 않고 이미 설치된 것만 씁니다.)
- Node.js 20 이상 (프런트엔드 테스트용. v24.20.0, npm 11.19.0에서 확인했고 20·22는 확인하지 않았습니다. Node 22 미만에서는 JavaScript 코드의 줄 실행 검증이 "실행하지 않음"으로 건너뛰어집니다.)
- 선택: Java 소스 실행 검증에는 Java 11 이상, Kotlin/JVM 원본 실행 검증에는 `kotlinc`와 Java, Rust 원본 실행 검증에는 `rustc`가 필요합니다. C++ 코드 실행 검증에는 C++17을 지원하는 `g++`가, C 코드 실행 검증에는 `gcc`와 `gcov`가, Python 코드 실행 검증에는 `python`(3.8 이상)이 PATH에 필요합니다. 없으면 해당 테스트는 이유를 표시하고 건너뜁니다(통과한 것으로 세지 않습니다).
- Supabase 프로젝트는 회원·게시판 단계부터 필요합니다. 학습 화면만 실행할 때는 필요 없습니다. 실제 프로젝트 설정·검증 기록은 docs/ACCEPTANCE.md 마지막 절을 참고하세요.

## 로컬 실행 (PowerShell)

```powershell
cd mobile/web/backend
python -m venv .venv
.\.venv\Scripts\python.exe -m pip install -r requirements.txt
.\.venv\Scripts\python.exe -m uvicorn app.main:app --reload
```

http://127.0.0.1:8000/ 에서 확인합니다. 자세한 내용과 백엔드 테스트는 [backend/README.md](backend/README.md)를 보세요.

## 테스트

```powershell
# 백엔드 (mobile/web/backend)
.\.venv\Scripts\python.exe -m pip install -r requirements-dev.txt
.\.venv\Scripts\python.exe -m pytest

# 프런트엔드 (mobile/web)
npm test
# 실행기가 없어 건너뛴 테스트가 있는지는 요약의 "skipped"와 테스트 이름 옆의 "실행하지 않음"으로 확인합니다.

# 예제 콘텐츠(trace) 검증: catalog에 등록된 모든 예제를 검사한다 (mobile/web)
npm run validate:traces

# DB(migration·RLS·RPC) 테스트: 설치된 PostgreSQL로 일회용 임시 클러스터를 띄워 실행하고 지운다 (mobile/web)
# PostgreSQL이 없으면 "실행하지 않음"을 알리고 종료 코드 2로 끝난다(통과가 아니다). npm test에도 같은 테스트가 있으며 없으면 건너뛴다.
npm run test:db

# 예제 데이터는 scripts/trace-builders.mjs(코드·줄 매핑·설명·단계 생성)에서 만든다. 고친 뒤 다시 만들고(version을 올린다) 검증한다.
node scripts/generate-traces.mjs
```

## 주의

- `backend/.env`에는 비밀 키가 들어갑니다. Git에 올리지 마세요(`.gitignore`로 제외되어 있습니다). 값은 문서나 테스트에 복사하지 않습니다.
- 공개 배포(GitHub Pages, 외부 서버)는 이 프로젝트의 범위가 아닙니다. 소스를 GitHub에 올리고 로컬에서 실행·시연합니다.


## 언어 확장 정책 (2026-10-16)

표시 순서는 **Python → C++ → Java → Kotlin → Rust → C → JavaScript**이며 일곱 언어 모두 네 알고리즘의 16개 예제에서 선택할 수 있다. 기본 언어는 Python이고 언어 전환 시 현재 단계와 시각화 상태를 유지한다. C++17·Java·Kotlin/JVM·Rust 코드를 제공한다. 각 언어의 한국어 설명과 eventId별 강조 줄을 함께 관리하며 코드 변경 시 contentVersion을 올린다. 실제 실행 검증 여부와 도구 환경의 제한은 docs/ACCEPTANCE.md에 기록한다. 과거 단계별 검증 기록은 당시 이력으로 보존한다.
