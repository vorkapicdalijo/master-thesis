export class ReviewId {
    productId: number;
    userId: string;
}

export class Review {
    reviewId: ReviewId;
    userName: string;
    rating: number;
    comment: string;
    createdAt: Date;
}