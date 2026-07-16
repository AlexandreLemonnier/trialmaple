export const USER_TYPES = ['USER', 'ADMIN'] as const;
export type UserType = typeof USER_TYPES[number];

export type User = {
    discordId: string;
    username: string;
    avatar: string | null;
    discriminator: string | null;
    userType: UserType;
};
