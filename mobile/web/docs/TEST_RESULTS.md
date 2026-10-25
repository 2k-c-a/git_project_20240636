# 13단계 통합·보안·모바일 검증

검증일: 2026-10-25. 판정: **로컬 자동 검사·일부 Chrome 화면 검사 통과, 실제 Supabase·실기기 검증 대기**. 전체 완료로 체크하지 않는다.

## 환경과 범위

- Windows의 저장소 가상환경과 Node, Chrome 확장 브라우저를 사용했다.
- 임시 Uvicorn 서버는 127.0.0.1:8768에서 인증 미설정 AppConfig와 메모리 게시글 저장소를 주입해 실행했다. 실제 backend/.env와 Supabase에는 접속하지 않았다.
- 기존 2026-10-17 사용자 보고의 이메일 가입·확인·로그인·로그아웃·복구 성공은 별도 과거 근거로 유지한다. 새 CSP가 적용된 현재 버전을 실제 Supabase에서 검증한 근거는 아니다.

## 자동 검사 결과

| 검사 | 결과 | 한계 |
|---|---|---|
| backend pytest 전체 | 460개 통과, 실제 Supabase 통합 1개 건너뜀 | DB·Auth 모의 경계 및 SDK 요청 검사 포함. 실제 계정 권한은 별도 |
| npm test 전체 | 정상 종료 | 로컬 DB 임시 클러스터·기존 프런트엔드 검사 포함 |
| validate:traces | 16개 예제 통과 | 콘텐츠·전이·줄 범위 검사. 실제 언어별 의미 검토는 기존 기록 참조 |
| git diff --check | 통과 | Git의 LF/CRLF 안내는 공백 오류와 구분 |

백엔드에는 기존 Starlette/httpx 사용 중단 예정 경고 1개가 남아 있다.

## 보안 헤더 구현과 검증

- 사이트 CSP는 script/style self, object none, base-uri none, form-action self, frame-ancestors none을 사용한다. unsafe-inline·unsafe-eval과 Supabase 도메인 와일드카드는 허용하지 않는다.
- connect-src는 같은 origin과 설정된 Supabase URL의 정확한 HTTP(S)/WS(S) origin만 사용한다. 키·토큰·URL 경로를 정책에 넣지 않는다. 설정된 로컬 Supabase 포트는 유지한다.
- map.html에만 maps.google.com·www.google.com frame을 허용한다. 콜백·게시판·시각화 등 다른 페이지의 frame-src는 none이다.
- nosniff, X-Frame-Options DENY, Referrer-Policy no-referrer, 카메라·마이크·위치 권한 비활성화를 추가했다. 성공·리다이렉트·401/404/503·예상 밖 500의 헤더 적용을 검사했다.
- 로컬 개발용 /docs·/redoc·/docs/oauth2-redirect는 생성된 inline script와 CDN을 사용하므로 사이트 CSP만 제외한다. 다른 공통 헤더는 적용된다. 이 예외를 일반 사이트 페이지에 확대하지 않는다.
- 참고: [MDN CSP](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CSP), [connect-src](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Content-Security-Policy/connect-src), [frame-src](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Content-Security-Policy/frame-src).

## Chrome 화면 검사 결과

| 시나리오 | 실행 결과 |
|---|---|
| 16개 예제 × 7개 언어 × 360/768/1280px | 336개 조합의 0단계와 마지막 단계가 슬라이더 범위와 일치. 전역 가로 넘침 0건 |
| 공통·회원 12페이지 × 3개 폭 | 36개 화면에서 전역 가로 넘침 0건 |
| 실제 브라우저 XSS 문자열 출력 | 메모리 글 제목의 script 문자열·본문의 img/onerror 문자열이 글자로 표시됨. 해당 영역에 script/img 요소 0개. 비회원 편집 버튼 숨김 |
| CSP 적용된 시각화 | 선택·언어 전환·슬라이더 처음/끝·자동 재생 시작 정상. 검사 시점의 브라우저 error/warn 0건 |
| 회원 기능 미설정 | 가입/로그인 화면에 한국어 안내가 표시됨 |
| 지도 | frame src와 외부 대체 링크는 존재, 검사 시점에 CSP 오류 로그는 없었으나 스크린샷에서 지도 타일은 빈 화면. 정상 렌더링 미확인, 원인 확정 안 함 |
| 실제 탭 숨김 | 다른 Chrome 검증 탭을 열어도 document.hidden=false로 유지됨. 실제 숨김 정지 검증 미실행으로 유지 |

처음/끝 검사 결과를 모든 중간 단계의 시각적 정확성·잘림 검증으로 확대하지 않는다. 12페이지는 index·board·info·idea·map·gallery·contact·login·signup·profile·auth-callback·reset-password이며, 게시글 상세는 별도 XSS 읽기 시나리오에서 검사했다.

## 남은 실제 통합 시나리오

1. 개발 Supabase에 실제 계정 A/B를 준비하고 backend/tests/integration/README.md의 명시적 선택 실행 테스트를 수행한다. 본인 테스트 글만 생성·정리한다.
2. A 가입→이메일 확인→프로필 자동 생성/보완→작성→목록→수정→삭제를 현재 CSP가 적용된 실제 화면에서 확인한다.
3. B에는 A 글 편집 버튼이 없고 API·direct Data API도 타인 변경을 거부하는지 확인한다.
4. 같은 ETag의 동시 수정 하나만 성공하고 다른 요청은 충돌하는지 확인한다. 401 재로그인·412 비교·404 삭제·응답 유실에서 초안이 보존되는지 실제 화면으로 확인한다.
5. 새 CSP에서 실제 Auth 로그인·갱신·확인/복구 callback 및 Google 지도 렌더링을 확인한다. 메일·토큰·비밀번호는 기록에 적지 않는다.
6. 일반 브라우저의 실제 탭 전환, 모바일 실기기 회전, Safari와 스크린 리더 낭독을 확인한다. 모든 중간 단계의 강조 코드 가독성·영역 내부 스크롤도 점검한다.

## 재실행

```powershell
cd mobile/web/backend
.\.venv\Scripts\python.exe -m pytest -q
cd ..
& 'C:/Program Files/nodejs/npm.cmd' test
& 'C:/Program Files/nodejs/npm.cmd' run validate:traces
```

일반 실행에는 실제 Supabase 선택 테스트를 켜지 않는다. 실제 계정 검증 결과 없이 13단계 완료로 표시하지 않는다.


## 14단계 새 환경 재현 (2026-10-25)

- 아직 커밋하지 않은 변경을 포함한 현재 작업트리에서 Git 포함 대상 소스 189개를 별도 임시 디렉터리로 복사했다. 기존 .env·.venv·node_modules·캐시는 복사하지 않았다. GitHub의 과거 커밋을 clone해 최신 변경까지 검증했다는 의미는 아니다.
- 그 디렉터리에 새 .venv를 만들고 requirements-dev.txt로 새 패키지 설치를 수행했다. pip check: No broken requirements found. 백엔드 460개 통과·실제 Supabase 통합 1개 건너뜀, npm 전체 정상 종료, trace 16개 통과.
- 새 가상환경의 Uvicorn을 실제 임시 포트로 실행해 health, 시각화 HTML, catalog JSON, 가입 HTML, 인증 미설정 config 응답과 nosniff를 확인했다. 테스트 서버와 임시 소스/가상환경 디렉터리를 정리했다.
- README와 backend/supabase 안내 및 DEMO·THIRD_PARTY·TEST_RESULTS·SDK 안내의 상대 문서 링크 오류 0개. git diff --check 통과.
- .env·.venv 제외와 .env.example 추적을 확인했다. Git 포함 대상 텍스트의 키/토큰/비밀번호 포함 DB URL/개인 키 패턴 검사에서 실제 비밀값 의심 경로 0개(명시적인 테스트용 SENTINEL 가짜 값은 구분). .env 내용을 읽은 것은 아니다. staged 파일은 0개여서 실제 업로드 직전 staged diff 검사는 다음 commit/push 작업에서 다시 수행한다. Git 전체 과거 이력 감사는 하지 않았다.
- SDK MIT 라이선스 전문과 출처를 보존했다. 기존 사진·음악·아이콘의 정확한 제작/허락 기록은 확보하지 못했다. 새 공개 배포 준비 완료로 간주하지 않는다.
- 본인 Supabase의 Auth/메일/계정 A/B까지 새로 구성해 재현하는 과정, 현재 CSP의 실제 Auth·지도, 모바일 실기기·Safari·스크린 리더는 미실행이다. 13단계 미확인 조건은 그대로 남는다. 실제 commit/push·공개 배포는 수행하지 않았다.
