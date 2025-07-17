package gift.domain.member.dto;

import org.springframework.data.domain.Page;

public record MemberInfoPageResponse(Page<MemberInfoResponse> memberInfoResponses) {
}
