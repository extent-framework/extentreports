<#assign
	categoryCtx=report.categoryCtx.set>
<div class="test-wrapper row tag-view attributes-view">
  <div class="test-list">
    <div class="test-list-tools">
      <ul class="tools pull-left"><li><a href=""><span class="font-size-14">Tags</span></a></li></ul>
      <ul class="tools text-right"><li><a href="#"><span class="badge badge-primary">${categoryCtx?size}</span></a></li></ul>
    </div>
    <div class="test-list-wrapper scrollable">
      <ul class="test-list-item">
        <#list categoryCtx as ctx>
        <li class="test-item">
          <div class="open-test">
            <div class="test-detail">
              <span class="meta">
              <#if ctx.passed!=0><span class='badge badge-success'>${ctx.passed}</span></#if>
              <#if ctx.failed!=0><span class='badge badge-danger'>${ctx.failed}</span></#if>
              <#if ctx.skipped!=0><span class='badge badge-skip'>${ctx.skipped}</span></#if>
              <#if ctx.others!=0><span class='badge badge-warning'>${ctx.others}</span></#if>
              </span>
              <p class="name">${ctx.attr.name}</p>
              <p class="duration text-sm">${ctx.size()} tests</p>
            </div>
          </div>
          <div class="test-contents d-none">
            <div class="info">
              <h4>${ctx.attr.name}</h4>
              <#if ctx.passed!=0><span status="pass" class='badge badge-success'>${ctx.passed} passed</span></#if>
              <#if ctx.failed!=0><span status="fail" class='badge badge-danger'>${ctx.failed} failed</span></#if>
              <#if ctx.skipped!=0><span status="skip" class='badge badge-skip'>${ctx.skipped} skipped</span></#if>
              <#if ctx.others!=0><span status="skip" class='badge badge-warning'>${ctx.others} others</span></#if>
            </div>
            <table class='table table-sm mt-4'>
              <thead>
                <tr>
                  <th class="status-col">Status</th>
                  <th class="timestamp-col">Timestamp</th>
                  <th>TestName</th>
                </tr>
              </thead>
              <tbody>
                <#list ctx.testList as test>
                <tr class="tag-test-status" status="${test.status.toLower()}">
                  <td>
                    <div class='status-avatar ${test.status.toLower()}-bg'>
                      <i class="fa fa-${Ico.ico(test.status)} text-white"></i>
                    </div>
                  </td>
                  <td>${test.startTime?string[("HH:mm:ss a")]}</td>
                  <td class='linked' test-id='${test.getId()}'>
                    ${test.name}
                    <#if test.parent??>
                    <div class="">
                      <span class="badge badge-default">${TestService.fullName(test)}</span>
                    </div>
                    </#if>
                  </td>
                </tr>
                </#list>
              </tbody>
            </table>
          </div>
        </li>
        </#list>
      </ul>
    </div>
  </div>
  <div class="test-content scrollable">
    <div class="test-content-tools">
      <ul>
        <li>
          <a class="back-to-test" href="javascript:void(0)">
          <i class="fa fa-arrow-left"></i>
          </a>
        </li>
      </ul>
    </div>
    <div class="test-content-detail">
      <div class="detail-body"></div>
    </div>
  </div>
</div>