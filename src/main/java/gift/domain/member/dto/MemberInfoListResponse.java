package gift.domain.member.dto;

import org.springframework.data.domain.Page;

public record MemberInfoListResponse(Page<MemberInfoResponse> memberInfoResponses) {
}
