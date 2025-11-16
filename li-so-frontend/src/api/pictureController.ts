// @ts-ignore
/* eslint-disable */
import request from "@/request";

/** listPictureByPage POST /api/picture/list/picture */
export async function listPictureByPageUsingPost(
  body: API.PictureRequest,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponsePagePicture_>("/api/picture/list/picture", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
    ...(options || {}),
  });
}
