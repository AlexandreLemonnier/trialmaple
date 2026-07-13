import type { User } from '#/types/api/user';

export type LoginResponse = {
    token: string;
    user: User;
};
