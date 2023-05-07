# Coindesk api

## APIs
- 呼叫coindesk api: `GET   /api/callCoindesk`
- 查詢幣別: `GET   /api/currencies/name`
- 新增幣別: `POST   /api/currencies`
- 修改幣別: `PATCH   /api/currencies/name`
- 新增幣別: `DELETE   /api/currencies`

## Unit Tests (/test/java/com/danielliao/coindesk_api)
- CoindeskApiTest.java:
	- `testCallCoindeskApi()`: 測試呼叫coindesk API
	- `testCallConvertedApi()`: 測試呼叫資料轉換的API
- CurrencyRestControllerTest:
	- `testGetCurrency()`: 測試呼叫查詢幣別對應表資料API
	- `testAddCurrency()`: 測試呼叫新增幣別對應表資料API
	- `testUpdateCurrency()`: 測試呼叫更新幣別對應表資料API
	- `testDeleteCurrency()`: 測試呼叫刪除幣別對應表資料API